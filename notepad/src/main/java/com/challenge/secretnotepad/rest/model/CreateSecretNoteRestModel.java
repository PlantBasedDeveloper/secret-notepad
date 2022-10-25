package com.challenge.secretnotepad.rest.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) which is mapped to the POST Request body that aims to create a new secret note.
 * It is processed by the {@link com.challenge.secretnotepad.rest.SecretNotepadController SecretNotepadController}.
 * <p>
 * @author Mohamed Amine Allani
 */
@Data
public class CreateSecretNoteRestModel {
    @NotEmpty(message = "Title cannot be blank")
    @Size(min = 5, max = 50, message = "Title length must be between 5 and 50 characters.")
    private final String title;

    @NotEmpty(message = "Note cannot be blank")
    @Size(min = 5, max = 500, message = "Note length must be between 5 and 500 characters.")
    private final String note;
}
