package eventTracer;

import com.sun.xml.internal.ws.api.server.*;

import java.awt.*;
import java.awt.Container;
import java.beans.*;
import java.lang.reflect.*;

/**
 * Created by Elvis on 30.06.2016.
 */
public class EventTracer {
    private InvocationHandler handler;

    public EventTracer() {
        //обработчик всех событий в виде прокси-объектов
        handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println(method + ";" + args[0]);
                return null;
            }
        };

    }

    //добавляет объекты для отслеживания всех событий, которые может принимать данный компонент и производные от него компоненты
    public void add(Component c) {
        try {
            //получить все события,которые может принимать данный компонент
            BeanInfo info = Introspector.getBeanInfo(c.getClass());

            EventSetDescriptor[] evenSets = info.getEventSetDescriptors();
            for (EventSetDescriptor evenSet : evenSets)
                addListener(c, evenSet);
        } catch (IntrospectionException e) {
        }
        //если генерируется исключение , приемники событий не вводятся
        if (c instanceof Container) {
            //получить все производные объекты и организовать
            //рекурсивный вызов метода add()
            for (Component comp : ((Container) c).getComponents()) add(comp);
        }
    }

    //добавляет приемник заданного множества событий
    public void addListener(Component c, EventSetDescriptor evenSet) {
        //создать прокси-объект для данного типа приемника событийи
        //направить все вызовыэтому обработчику
        Object proxy = Proxy.newProxyInstance(null, new Class[]{evenSet.getListenerType()}, handler);

        //добавить прокси-объект как приемник событий в компонент
        Method addListenerMethod = evenSet.getAddListenerMethod();
        try {
            addListenerMethod.invoke(c, proxy);
        }
        catch (ReflectiveOperationException e) {
        }
        //если генерируется исключение, приемник событий не вводится
    }
}
