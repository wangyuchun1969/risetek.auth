package com.risetek.auth.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.gwtplatform.dispatch.rpc.shared.Result;

@SuppressWarnings("serial")
public class GetResults<T extends IsSerializable> implements Result {
	List<T> results;

    protected GetResults() {}

    public GetResults(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }
}
