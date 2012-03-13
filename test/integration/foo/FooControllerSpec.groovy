package foo

import grails.plugin.spock.*

class FooControllerSpec extends ControllerSpec {
	def 'unsafe params are not bound using constructor'() {
		setup:
			controller.params.name = 'test'
			controller.params.admin = 'true'

		when:
			def model = controller.index()

		then:
			model.foo != null
			model.foo.admin == true
			model.safeFoo != null
			model.safeFoo.admin == false
	}

	def 'unsafe params are not bound using properties setter'() {
		setup:
			controller.params.name = 'test'
			controller.params.admin = 'true'

		when:
			def model = controller.test()

		then:
			model.foo != null
			model.foo.admin == true
			model.safeFoo != null
			model.safeFoo.admin == false
	}
}