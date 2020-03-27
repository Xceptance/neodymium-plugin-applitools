package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import util.applitools.ApplitoolsApi;

@RunWith(Suite.class)
@SuiteClasses(
{
  ApplitoolsApi.class, ApplitoolsApiExceptionsTest.class, ApplitoolsApiWithValidApiKeyTest.class
})
public class SuiteForTestUsingDevProperties
{

}
