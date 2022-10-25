package com.challenge.secretnotepad.rest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * DTO (Data Transfer Object) which is mapped to the Response body to the GET request that aims to fetch a given secret note.
 * It is returned by the {@link com.challenge.secretnotepad.rest.SecretNotepadController SecretNotepadController}.
 * <p>
 * @author Mohamed Amine Allani
 */
@Data
@Builder
public class GetSecretNoteRestModel {
    private final Long id;
    private final String title;
    private final String note;
    private final Date createdAt;
    private final Date lastModifiedAt;
}
