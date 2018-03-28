
package com.ice.utils;

import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.ice.exception.IceException;

/**
 * @author <a href="mailto:gengcai.xie@skyroam.com">Xie Gengcai</a>
 * @version 1.0
 */
public class XmlUitls  {

    private static Map<Class<?>, JAXBContext> jaxbContextMap = new ConcurrentHashMap<Class<?>, JAXBContext>();

    public static String toXml(Object object) {
        try {
            if (!jaxbContextMap.containsKey(object.getClass())) {
                JAXBContext context = JAXBContext.newInstance(object.getClass());
                jaxbContextMap.put(object.getClass(), context);
            }
            JAXBContext context = jaxbContextMap.get(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            
            StringWriter writer = new StringWriter();
            
            marshaller.marshal(object, writer);
            
            return writer.toString();
            
        } catch (JAXBException e) {
            throw new IceException(e);
        }
    }
}

