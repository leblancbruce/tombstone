package com.tombstone.server.ui.admin.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.lebcool.common.logging.Logger;

@ManagedBean
@ViewScoped
public final class ImageBean implements Serializable
{
    public ImageBean()
    {
        LOGGER.info(this, "Created this bean.");
    }


    private static final Logger LOGGER = new Logger(ImageBean.class);

    private static final long serialVersionUID = 1L;
}
