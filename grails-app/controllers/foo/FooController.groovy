package foo

class FooController {

	def index = {
		def foo = new Foo(params)
		def safeFoo = new SafeFoo(params)

		return [foo: foo, safeFoo: safeFoo]
	}

	def test = {
		def foo = new Foo()
		foo.properties = params
		def safeFoo = new SafeFoo()
		safeFoo.properties = params

		return [foo: foo, safeFoo: safeFoo]
	}

	def manual = {
		def safeFoo = new SafeFoo(name: 'test', admin: true)
		return [safeFoo: safeFoo]
	}

}