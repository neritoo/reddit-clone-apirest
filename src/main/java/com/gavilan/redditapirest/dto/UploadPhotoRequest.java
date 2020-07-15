package com.gavilan.redditapirest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Eze Gavilán
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPhotoRequest {

    private String username;
    private String photoName;
}
