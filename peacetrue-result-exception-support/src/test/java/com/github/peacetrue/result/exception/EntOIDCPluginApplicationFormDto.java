package com.github.peacetrue.result.exception;


import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class EntOIDCPluginApplicationFormDto implements Serializable {

    @NotNull
    @Size(max = 32)
    private String name;

    @Size(max = 255)
    private String authorizedGrantTypes;

    @URL
    @Size(max = 255)

    private String webServerRedirectUri;

    private Integer idTokenValidity = 7200;

    private Integer accessTokenValidity = 7200;

    private Integer refreshTokenValidity = 604800;

    private Integer authorizeCodeValidity = 60;

    private Integer overlap = 7200;

    @NotNull

    @Size(max = 255)

    private String scope;

    @Size(max = 1024)

    private String claims;

}
