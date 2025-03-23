package org.example.mvc.view;

public class JspViewResolver implements ViewResolver{
    @Override
    public View resolveView(String viewName) {
        if (viewName.startsWith("redirect:")){
            return new RedirectView(viewName);

        }

        return new JspView(viewName+".jsp");
    }
}
