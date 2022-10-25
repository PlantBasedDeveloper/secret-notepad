package com.challenge.secretnotepad.rest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * DTO (Data Transfer Object) which is mapped to the Response body to the GET request that aims to fetch a summary of all secret notes.
 * It is returned as a list by the {@link com.challenge.secretnotepad.rest.SecretNotepadController SecretNotepadController}.
 * <p>
 * @author Mohamed Amine Allani
 */
@Data
@Builder
public class SecretNoteSummaryRestModel {
    private final Long id;
    private final String title;
    private final Date createdAt;
    private final Date lastModifiedAt;
}
