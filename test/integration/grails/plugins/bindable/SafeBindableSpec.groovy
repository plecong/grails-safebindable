package grails.plugins.bindable

import grails.plugin.spock.*
import foo.*

class SafeBindableSpec extends IntegrationSpec  {

	def 'Can set all properties on unsafe'() {
		when:
			def foo = new Foo(name: 'test', admin: true)

		then:
			foo.admin == true
	}

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

	def 'Can set only bindable properties on safe'() {
		when:
			def foo = new SafeFoo(name: 'test', admin: true)

		then:
			foo.admin == false
	}

}