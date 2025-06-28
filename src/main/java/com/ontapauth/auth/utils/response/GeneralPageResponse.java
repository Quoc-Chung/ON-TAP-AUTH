package com.ontapauth.auth.utils.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeneralPageResponse<T> implements Serializable {

  private ResponseStatus status;
  private List<T> data;

  private PageResponse page;

  private Map<String, Object> extraData;


  public GeneralPageResponse() {
  }


  public GeneralPageResponse(ResponseStatus status, List<T> data, PageResponse pageResponse) {
    this.status = status;
    this.data = data;
    this.page = pageResponse;
  }


  public GeneralPageResponse(ResponseStatus status, List<T> data, PageResponse pageResponse,
      Map<String, Object> extraData) {
    this.status = status;
    this.data = data;
    this.page = pageResponse;
    this.extraData = extraData;
  }

}

