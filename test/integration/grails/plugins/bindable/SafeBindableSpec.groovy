package grails.plugins.bindable

import grails.plugin.spock.*
import foo.*

class SafeBindableSpec extends IntegrationSpec  {

	def 'Beans injected on unsafe'() {
		when:
			def foo = new Foo(name: 'test', admin: true)
		then:
			foo.grailsApplication != null
	}

	def 'Beans injected on safe'() {
		when:
			def foo = new SafeFoo(name: 'test', admin: true)
		then:
			foo.grailsApplication != null
	}

}