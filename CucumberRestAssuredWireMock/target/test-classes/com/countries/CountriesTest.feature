@CountriesTest
Feature: All Countries Rest Service
	As a user
	I want to be able to see all countries in the world
	So that I can then choose which country to see further details about

	@AllCountries @Smoke
	Scenario: All countries Returns HTTP 200
		Given the countries endpoint is configured
		When  the user calls Countries web service
		Then the service returns HTTP 200

	@AllCountries @Regression
	Scenario: All Countries Validate Headers
		Given the countries endpoint is configured
		When  the user calls Countries web service
		Then  User gets success response

	@AllCountries @Regression
	Scenario: All Countries Validate Response Time
		Given the countries endpoint is configured
		When  the user calls Countries web service
		Then  the countries service responds in 4 seconds

