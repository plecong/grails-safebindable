import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.codehaus.groovy.grails.web.util.TypeConvertingMap
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.codehaus.groovy.grails.web.binding.DataBindingUtils

class SafeBindableGrailsPlugin {

    private Logger log = LoggerFactory.getLogger('grails.plugin.safebind.SafeBindableGrailsPlugin')

    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.3.7 > *"
    // the other plugins this plugin depends on
    def dependsOn = [domainClass: "1.0 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
            "grails-app/domain/foo",
            "grails-app/controllers/foo",
    ]

    // TODO Fill in these fields
    def author = "Phuong LeCong"
    def authorEmail = "plecong@gmail.com"
    def title = "Allow whitelisting of properties to bind to Domain classes"
    def description = '''\\
Implements the dataBindable static attribute on Domain classes to avoid mass assignment vulnerability.
See http://blog.adamcreeger.com/2012/03/grails-rails-github-and-mass-assignment.html
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/safe-bindable"

    def doWithWebDescriptor = { xml -> }
    def doWithSpring = { }
    def doWithApplicationContext = { applicationContext -> }

    def doWithDynamicMethods = { ctx ->
        application.domainClasses.each { dc -> updateDomainClass(dc) }
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    private void updateDomainClass(def dc) {
        MetaClass metaClass = dc.metaClass
        def bindableParams = dc.getStaticPropertyValue('dataBindable', List)

        if (bindableParams != null) {
            metaClass.constructor = { Map map ->
                def instance = metaClass.invokeConstructor()
                def included = (map instanceof TypeConvertingMap || map instanceof GrailsParameterMap) ? bindableParams : null
                DataBindingUtils.bindObjectToDomainInstance(dc, instance, map, included, Collections.EMPTY_LIST, null)
                DataBindingUtils.assignBidirectionalAssociations(instance, map, dc)
                return instance
            }
            metaClass.setProperties = { Object o ->
                def included = (o instanceof TypeConvertingMap || o instanceof GrailsParameterMap) ? bindableParams : null
                DataBindingUtils.bindObjectToDomainInstance(dc, delegate, o, included, Collections.EMPTY_LIST, null)
            }
        }
    }
}
