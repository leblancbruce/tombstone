package com.tombstone.server.exception.handler;

import static com.tombstone.server.ui.admin.bean.Page.DEFAULT_ERROR_PAGE;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.lebcool.common.logging.Logger;

public final class TombstoneExceptionHandlerFactory
    extends ExceptionHandlerFactory
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public TombstoneExceptionHandlerFactory(
        final ExceptionHandlerFactory parent)
    {
         _parent = parent;
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

     @Override
     public ExceptionHandler getExceptionHandler() {

         final ExceptionHandler handler = new TombstoneExceptionHandlerWrapper(
             _parent.getExceptionHandler());

         return handler;
     }

     @Override
     public String toString()
     {
         return getClass().getName() + "[parent=" + _parent + "]";
     }

     //:: ---------------------------------------------------------------------
     //:: Private Nested Classes

     private static final class TombstoneExceptionHandlerWrapper
          extends ExceptionHandlerWrapper
     {
         public TombstoneExceptionHandlerWrapper(
             final ExceptionHandler exceptionHandler)
         {
             _exceptionHandler = exceptionHandler;
         }

         @Override
         public ExceptionHandler getWrapped() {
             return _exceptionHandler;
         }

         @Override
         public void handle() throws FacesException
         {
             // TODO - iterate through the unhandled exceptions to gather the
             //        exception messages to present to the user.
             if(getUnhandledExceptionQueuedEvents().iterator().hasNext())
             {
                 LOGGER.info(this, "An uncaught exception was thrown.  Attempting "
                     + "to display the default error page.");

                 final FacesContext facesContext
                     = FacesContext.getCurrentInstance();

                 final ExternalContext externalContext
                     = facesContext.getExternalContext();

                 try
                 {
                     externalContext.redirect(DEFAULT_ERROR_PAGE + ".jsf");
                 }
                 catch(final IOException ioe)
                 {
                     LOGGER.error(this, "An exception was encountered while "
                         + "trying to redirect to the page=\""
                         + DEFAULT_ERROR_PAGE + "\".", ioe);
                 }
             }
         }

         @Override
         public String toString()
         {
             return getClass().getName() + "[exceptionHandler="
                 + _exceptionHandler + "]";
         }

         private final ExceptionHandler _exceptionHandler;

         private static final Logger LOGGER
             = new Logger(TombstoneExceptionHandlerWrapper.class);
     }

     //:: ---------------------------------------------------------------------
     //:: Private Data Members

     private final ExceptionHandlerFactory _parent;
}
