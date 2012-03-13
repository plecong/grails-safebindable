package foo

class FooController {

	def index = {
		def foo = new Foo(params)
		def safeFoo = new SafeFoo(params)

		return [foo: foo, safeFoo: safeFoo]
	}

}