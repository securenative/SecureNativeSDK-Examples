package main

import (
	"github.com/securenative/securenative-go-demo/demo/server"
	"os"
)

func main() {
	host := os.Getenv("GIN_HOST")
	if len(host) <= 1 || host == "" {
		host = "0.0.0.0:8080"
	}

	s := server.NewDemoServer(host)
	s.Serve()
}
