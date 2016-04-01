package org.checker;

import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 */
public class App {
    List<String> expected = Arrays.asList(
            "org.checker.App",
            "org.checker.TestApp",
            "org.checker.TestObject");

    public static void main(String[] args) throws IllegalConnectorArgumentsException, IOException {
        System.out.println("Hello World!");
        App app = new App();
        app.testStartedAllClasses();
        System.out.println();
    }

    /**
     * Test JDI allClasses() and JDWP 'VM- Get all classes'
     * once the test program has been started.
     */
    public void testStartedAllClasses() throws IllegalConnectorArgumentsException, IOException {

        // The test program has started, the number of classes is != 0
        VirtualMachine vm = connect(getConnector(), String.valueOf(8858));
        List<ReferenceType> classes = vm.allClasses();

        // Collect names of received classes

        ListIterator<?> iterator = classes.listIterator();
        List<ReferenceType> expectedClasses = new ArrayList<ReferenceType>();
        while (iterator.hasNext()) {
            ReferenceType type = (ReferenceType) iterator.next();


        }
        List<ReferenceType> anotherList = definedClasses(classes);
        System.out.println(expectedClasses.size());
        vm.classesByName("org.checker.TestObject").get(0).instances(0) ;
        // Check that they are the expected names

    }
    public List<ReferenceType> definedClasses(List<ReferenceType> classes) {
        // Note that this information should not be cached.
        List<ReferenceType> visibleClasses = classes;
        List<ReferenceType> result = new ArrayList<ReferenceType>(visibleClasses.size());
        Iterator<ReferenceType> iter = visibleClasses.iterator();
        while (iter.hasNext()) {
            try {
                ReferenceType type = iter.next();
                // Note that classLoader() is null for the bootstrap
                // classloader.
                if (type.classLoader() != null && type.classLoader().equals(this))
                    result.add(type);
            } catch (ClassNotPreparedException e) {
                continue;
            }
        }
        return result;
    }
    public VirtualMachine connect(int port)
            throws IOException {
        String strPort = Integer.toString(port);
        AttachingConnector connector = getConnector();
        try {
            VirtualMachine vm = connect(connector, strPort);
            return vm;
        } catch (IllegalConnectorArgumentsException e) {
            throw new IllegalStateException(e);
        }
    }

    private AttachingConnector getConnector() {
        VirtualMachineManager vmManager = Bootstrap
                .virtualMachineManager();
        for (Connector connector : vmManager
                .attachingConnectors()) {
            System.out.println(connector.name());
            if ("com.sun.jdi.SocketAttach".equals(connector
                    .name())) {
                return (AttachingConnector) connector;
            }
        }
        throw new IllegalStateException();
    }

    private VirtualMachine connect(
            AttachingConnector connector, String port)
            throws IllegalConnectorArgumentsException,
            IOException {
        Map<String, Connector.Argument> args = connector
                .defaultArguments();
        Connector.Argument pidArgument = args.get("port");
        if (pidArgument == null) {
            throw new IllegalStateException();
        }
        pidArgument.setValue(port);

        return connector.attach(args);
    }

}
