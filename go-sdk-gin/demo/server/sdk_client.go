package server

import (
	"github.com/securenative/securenative-go/context"
	"github.com/securenative/securenative-go/events"
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
	eventOptionsBuilder := events.NewEventOptionsBuilder(eventType)
	contextBuilder := context.NewSecureNativeContextBuilder()

	c := contextBuilder.WithIp(ip).WithClientToken(clientToken).WithHeaders(headers).Build()
	eventOptions, err := eventOptionsBuilder.WithUserId(userId).WithUserTraits(models.UserTraits{Name: userName, Email: email, Phone: phone}).WithContext(c).Build()
	if err != nil {
		return err
	}

	sn.Track(eventOptions)
	return nil
}

func Verify(sn *sdk.SecureNative, eventType string, ip string, clientToken string,
	headers map[string]string, userId string, userName string, email string, phone string) (*models.VerifyResult, error) {
	eventOptionsBuilder := events.NewEventOptionsBuilder(eventType)
	contextBuilder := context.NewSecureNativeContextBuilder()

	c := contextBuilder.WithIp(ip).WithClientToken(clientToken).WithHeaders(headers).Build()
	eventOptions, err := eventOptionsBuilder.WithUserId(userId).WithUserTraits(models.UserTraits{Name: userName, Email: email, Phone: phone}).WithContext(c).Build()
	if err != nil {
		return &models.VerifyResult{}, err
	}

	return sn.Verify(eventOptions), nil
}
