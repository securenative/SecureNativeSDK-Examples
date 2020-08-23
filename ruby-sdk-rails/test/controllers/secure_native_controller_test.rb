require 'test_helper'

class SecureNativeControllerTest < ActionDispatch::IntegrationTest
  test "should get track" do
    get secure_native_track_url
    assert_response :success
  end

  test "should get verify" do
    get secure_native_verify_url
    assert_response :success
  end

end
