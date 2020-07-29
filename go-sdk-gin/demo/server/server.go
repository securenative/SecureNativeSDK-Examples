package server

import (
	"encoding/json"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/securenative/securenative-go/config"
	"github.com/securenative/securenative-go/enums"
	"github.com/securenative/securenative-go/sdk"
	"math/rand"
	"net/http"
	"os"
	"strconv"
)

const loginPath = "api/v1/login"
const logoutPath = "api/v1/logout"
const trackPath = "api/v1/track"
const verifyPath = "api/v1/verify"

type DemoServer struct {
	Host         string
	Router       *gin.Engine
	DefaultEvent SecureNativeEvent
}

func NewDemoServer(host string) *DemoServer {
	gin.SetMode(gin.ReleaseMode)
	configBuilder := config.NewConfigurationBuilder()
	apiUrl := os.Getenv("SECURENATIVE_API_URL")
	apiKey := os.Getenv("SECURENATIVE_API_KEY")
	clientToken := os.Getenv("SECURENATIVE_CLIENT_TOKEN")

	if len(apiKey) == 0 || apiKey == "" {
		panic("Cannot start demo api without SecureNative api key")
	}
	if len(apiUrl) == 0 || apiUrl == "" {
		apiUrl = "https://api.securenative-stg.com/collector/api/v1"
	}
	_, err := sdk.InitSDK(configBuilder.WithApiUrl(apiUrl).WithApiKey(apiKey).WithLogLevel("DEBUG").Build())
	if err != nil {
		panic(fmt.Sprintf("Failed to init SecureNative sdk; %s", err))
	}

	return &DemoServer{
		Host:   host,
		Router: gin.Default(),
		DefaultEvent: SecureNativeEvent{
			Ip:          "113.6.126.40",
			ClientToken: clientToken,
			Headers: map[string]string{
				"user-agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36",
			},
			UserId:   strconv.Itoa(rand.Intn(20-1) + 1),
			UserName: "John Snow",
			Email:    "john@securenative.com",
			Phone:    "+12012673412",
		},
	}
}

func (server *DemoServer) Serve() {
	// Register endpoints here ^^
	server.Router.GET(loginPath, server.login)
	server.Router.GET(logoutPath, server.logout)
	server.Router.GET(trackPath, server.track)
	server.Router.GET(verifyPath, server.verify)

	err := server.Router.Run(server.Host)
	if err != nil {
		panic(err)
	}
}

func (server *DemoServer) login(c *gin.Context) {
	sn, err := sdk.GetInstance()
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	err = Track(sn, enums.EventTypes.LogIn, server.DefaultEvent.Ip, server.DefaultEvent.ClientToken,
		server.DefaultEvent.Headers, server.DefaultEvent.UserId, server.DefaultEvent.UserName,
		server.DefaultEvent.Email, server.DefaultEvent.Phone)
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusOK, "Tracked")
}

func (server *DemoServer) logout(c *gin.Context) {
	sn, err := sdk.GetInstance()
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	err = Track(sn, enums.EventTypes.LogOut, server.DefaultEvent.Ip, server.DefaultEvent.ClientToken,
		server.DefaultEvent.Headers, server.DefaultEvent.UserId, server.DefaultEvent.UserName,
		server.DefaultEvent.Email, server.DefaultEvent.Phone)
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusOK, "Tracked")
}

func (server *DemoServer) track(c *gin.Context) {
	eventType, found := c.GetQuery("eventType")
	if !found {
		c.JSON(http.StatusBadRequest, "Missing event type")
		return
	}

	ip, found := c.GetQuery("ip")
	if !found {
		ip = ""
	}

	clientToken, found := c.GetQuery("clientToken")
	if !found {
		clientToken = ""
	}

	var headers map[string]string
	h, found := c.GetQuery("headers")
	if !found {
		headers = nil
	} else {
		err := json.Unmarshal([]byte(h), &headers)
		if err != nil {
			c.JSON(http.StatusInternalServerError, err)
			return
		}
	}

	userId, found := c.GetQuery("userId")
	if !found {
		userId = ""
	}

	userName, found := c.GetQuery("userName")
	if !found {
		userName = ""
	}

	email, found := c.GetQuery("email")
	if !found {
		email = ""
	}

	phone, found := c.GetQuery("phone")
	if !found {
		phone = ""
	}

	sn, err := sdk.GetInstance()
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	err = Track(sn, eventType, ip, clientToken, headers, userId, userName, email, phone)
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusOK, "Tracked")
}

func (server *DemoServer) verify(c *gin.Context) {
	eventType, found := c.GetQuery("eventType")
	if !found {
		c.JSON(http.StatusBadRequest, "Missing event type")
		return
	}

	ip, found := c.GetQuery("ip")
	if !found {
		ip = ""
	}

	clientToken, found := c.GetQuery("clientToken")
	if !found {
		clientToken = ""
	}

	var headers map[string]string
	h, found := c.GetQuery("headers")
	if !found {
		headers = server.DefaultEvent.Headers
	} else {
		err := json.Unmarshal([]byte(h), &headers)
		if err != nil {
			c.JSON(http.StatusInternalServerError, err)
			return
		}
	}

	userId, found := c.GetQuery("userId")
	if !found {
		userId = ""
	}

	userName, found := c.GetQuery("userName")
	if !found {
		userName = ""
	}

	email, found := c.GetQuery("email")
	if !found {
		email = ""
	}

	phone, found := c.GetQuery("phone")
	if !found {
		phone = ""
	}

	sn, err := sdk.GetInstance()
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	result, err := Verify(sn, eventType, ip, clientToken, headers, userId, userName, email, phone)
	if err != nil {
		c.JSON(http.StatusInternalServerError, err)
		return
	}

	c.JSON(http.StatusOK, result)
}
