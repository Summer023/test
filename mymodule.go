// file: mymodule.go
package firstmodule

import "fmt"

// Hello returns a greeting message.
func Hello(name string) string {
	return fmt.Sprintf("Hello, %s!", name)
}
