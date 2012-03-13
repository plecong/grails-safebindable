package foo

import grails.plugin.spock.*

class FooControllerSpec extends ControllerSpec {
	def 'unsafe params are not bound'() {
		setup:
			controller.params.name = 'test'
			controller.params.admin = 'true'

		when:
			def model = controller.index()

		then:
			model.containsKey('foo')
			model.containsKey('safeFoo')
	}
}