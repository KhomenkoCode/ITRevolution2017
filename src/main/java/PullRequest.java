package main.java;

import com.google.gson.annotations.SerializedName;

class PullRequest {

    @SerializedName("url")
    String url;
    @SerializedName("html_url")
    String html_url;
    @SerializedName("diff_url")
    String diff_url;
    @SerializedName("patch_url")
    String patch_url;

}
