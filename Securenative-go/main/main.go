package main

import (
	"fmt"
	"github.com/securenative/securenative-go/snlogic"
)

func main() {
	//router := gin.New()
	cookie := "b492e4ac4aaacefc12e01e46a2eb1ae3a4b57623cca1b86cf6ba048c247119a6b770d1eefc7ce1b12005a9e82bf7614d274b9a98370792884e6f62d6f576b946ba1ba56bbef363b9ed0eb69e473603887cc81e104b9afd162fcc457f203f5a9972ef5673d22834378b9e4581dc6ba663040656fedc540b36d58b61cc06d445703c7564dfabdbaf4868a72f361d82d1bf"
	apikey := "6EA4915349C0AAC6F6572DA4F6B00C42DAD33E75"
	a, err := snlogic.Decrypt(cookie, apikey)
	fmt.Sprintf(a)
	fmt.Sprintf(err.Error())

}
