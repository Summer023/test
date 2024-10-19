// file: mymodule_test.go
package mymodule

import "testing"

func TestHello(t *testing.T) {
	result := Hello("Go")
	expected := "Hello, Go!"
	if result != expected {
		t.Errorf("expected %s, got %s", expected, result)
	}
}
