package com.example.shay.soundcloudexample.project.dataObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.shay.soundcloudexample.project.enums.OriginalFormatEnum;
import com.example.shay.soundcloudexample.project.parser.Parser;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by shay on 17/02/2016.
 */
public class Collection implements Serializable, Parcelable {

    private static final String TRACK = "track";
    private String kind;
    private int id;
    private String title;
    private boolean streamable;
    private String streamUrl;
    private String artworkUrl;
    private String originalFormat;


    public Collection(JSONObject jsonObject) {
        this.kind = Parser.jsonParse(jsonObject, "kind", Parser.createTempString());
        this.id = Parser.jsonParse(jsonObject, "id", this.id);
        this.title = Parser.jsonParse(jsonObject, "title", Parser.createTempString());
        this.streamable = Parser.jsonParse(jsonObject, "streamable", this.streamable);
        this.streamUrl = Parser.jsonParse(jsonObject, "stream_url", Parser.createTempString());
        this.artworkUrl = Parser.jsonParse(jsonObject, "artwork_url", Parser.createTempString());
        this.originalFormat = Parser.jsonParse(jsonObject, "original_format", Parser.createTempString());

    }

    public String getKind() {
        return kind;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStreamable() {
        return streamable;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public String getOriginalFormat() {
        return originalFormat;
    }

    public OriginalFormatEnum getOriginalFormatEnum() {
        if (this.getOriginalFormat().equals(OriginalFormatEnum.MP3.toString())) {
            return OriginalFormatEnum.MP3;
        }
        if (this.getOriginalFormat().equals(OriginalFormatEnum.RAW.toString())) {
            return OriginalFormatEnum.RAW;
        }
        if (this.getOriginalFormat().equals(OriginalFormatEnum.WAV.toString())) {
            return OriginalFormatEnum.WAV;
        }

        return OriginalFormatEnum.OTHER_FORMAT;

    }

    public boolean isTrack() {
        if (kind != null) {
            return kind.equals(TRACK);
        }

        return false;
    }



    protected Collection(Parcel in) {
        kind = in.readString();
        id = in.readInt();
        title = in.readString();
        streamable = in.readByte() != 0x00;
        streamUrl = in.readString();
        artworkUrl = in.readString();
        originalFormat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeByte((byte) (streamable ? 0x01 : 0x00));
        dest.writeString(streamUrl);
        dest.writeString(artworkUrl);
        dest.writeString(originalFormat);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
        @Override
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        @Override
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
}
