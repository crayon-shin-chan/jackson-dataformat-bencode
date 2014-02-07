package com.fasterxml.jackson.dataformat.bencode;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.dataformat.bencode.context.OutputContext;

import java.io.*;
import java.nio.charset.Charset;

public class BEncodeFactory extends JsonFactory { // TODO do we need to inherit from it?
    /**
     * Name used to identify JSON format
     * (and returned by {@link #getFormatName()}
     */
    public final static String FORMAT_NAME_JSON = "BEncode";

    public BEncodeFactory() {
        this(null);
    }

    public BEncodeFactory(ObjectCodec oc) {
        super(oc);
    }

    public BEncodeFactory(BEncodeFactory src, ObjectCodec codec) {
        super(src, codec);
    }

    @Override
    public BEncodeFactory copy() {
        _checkInvalidCopy(BEncodeFactory.class);
        return new BEncodeFactory(this, null);
    }

    @Override
    protected Object readResolve() {
        return new BEncodeFactory(this, _objectCodec);
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public boolean canHandleBinaryNatively() {
        return true;
    }

    @Override
    public String getFormatName() {
        return FORMAT_NAME_JSON;
    }

    @Override
    public MatchStrength hasFormat(InputAccessor acc) throws IOException {
        // TODO implement according to com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.hasJSONFormat()
        return MatchStrength.INCONCLUSIVE;
//        if (!acc.hasMoreBytes()) {
//            return MatchStrength.INCONCLUSIVE;
//        }
    }

    @Override
    public boolean canUseSchema(FormatSchema schema) {
        return super.canUseSchema(schema);
    }

    @Override
    public BEncodeGenerator createGenerator(OutputStream out, JsonEncoding enc) throws IOException {
        return new BEncodeGenerator(0, _objectCodec, new OutputContext(out, Charset.forName(enc.getJavaName()))); // TODO handle features
    }

    @Override
    public BEncodeGenerator createGenerator(OutputStream out) throws IOException {
        return createGenerator(out, JsonEncoding.UTF8);
    }

    @Override
    public BEncodeGenerator createGenerator(Writer out) throws IOException {
        throw new UnsupportedOperationException("BEncode doesn't support writer");
    }

    @Override
    public BEncodeGenerator createGenerator(File f, JsonEncoding enc) throws IOException {
        OutputStream os = new FileOutputStream(f); // , enc.getJavaName())
        return createGenerator(os, enc);
    }
}