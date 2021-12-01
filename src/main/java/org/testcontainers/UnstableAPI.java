package org.testcontainers;

import java.lang.annotation.*;

/**
 * Marks that the annotated API is a subject to change and SHOULD NOT be considered
 * a stable API.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({
    ElementType.TYPE,
    ElementType.METHOD,
    ElementType.FIELD,
})
@Documented
public @interface UnstableAPI {
}
