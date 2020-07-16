package util.applitools;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.xceptance.neodymium.NeodymiumRunner;

public class ApplitoolsRunner extends NeodymiumRunner
{

    public ApplitoolsRunner(Class<?> klass) throws InitializationError
    {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier)
    {
        notifier.addListener(new NeodymiumApplitoolsListerner());
        super.runChild(method, notifier);
    }

}
