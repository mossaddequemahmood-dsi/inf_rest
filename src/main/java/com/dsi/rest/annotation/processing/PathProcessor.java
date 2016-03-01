package com.dsi.rest.annotation.processing;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.dsi.rest.annotation.Path;

@Deprecated
public class PathProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		System.out.println("HELLO GUYS!");

		for (Element element : roundEnv.getElementsAnnotatedWith(Path.class)) {

			// Check if a class has been annotated with @Path
			if (element.getKind() != ElementKind.METHOD) {
				error(element, "Only method can be annotated with @%s",
						Path.class.getSimpleName());
				return true;
			}

			Element classElement = element.getEnclosingElement();
			boolean isPublic = element.getModifiers().contains(Modifier.PUBLIC);
			if (!isPublic) {

			}
		}
		return true;
	}

	private void error(Element e, String msg, Object... args) {
		processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
				String.format(msg, args), e);
	}
}
