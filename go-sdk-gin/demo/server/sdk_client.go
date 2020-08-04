package server

import (
	"github.com/securenative/securenative-go/context"
	"github.com/securenative/securenative-go/models"
	"github.com/securenative/securenative-go/sdk"
)

type SecureNativeEvent struct {
	Ip          string
	ClientToken string
	Headers     map[string]string
	UserId      string
	UserName    string
	Email       string
	Phone       string
}

func Track(sn *sdk.SecureNative, eventType string, ip string, clientToken string,
	headers map[string]string, userId string, userName string, email string, phone string) error {

	c := &context.SecureNativeContext{
		ClientToken: clientToken,
		Ip:          ip,
		Headers:     headers,
	}
	eventOptions := models.EventOptions{
		Event:      eventType,
		UserId:     userId,
		UserTraits: models.UserTraits{Name: userName, Email: email, Phone: phone, CreatedAt: nil},
		Context:    c,
	}

	sn.Track(eventOptions)
	return nil
}

func Verify(sn *sdk.SecureNative, eventType string, ip string, clientToken string,
	headers map[string]string, userId string, userName string, email string, phone string) (*models.VerifyResult, error) {

	c := &context.SecureNativeContext{
		ClientToken: clientToken,
		Ip:          ip,
		Headers:     headers,
	}
	eventOptions := models.EventOptions{
		Event:      eventType,
		UserId:     userId,
		UserTraits: models.UserTraits{Name: userName, Email: email, Phone: phone, CreatedAt: nil},
		Context:    c,
	}

	return sn.Verify(eventOptions), nil
}
