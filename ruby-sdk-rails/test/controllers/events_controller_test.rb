require 'test_helper'

class EventsControllerTest < ActionDispatch::IntegrationTest
  test "should get track" do
    get events_track_url
    assert_response :success
  end

  test "should get verify" do
    get events_verify_url
    assert_response :success
  end

end
