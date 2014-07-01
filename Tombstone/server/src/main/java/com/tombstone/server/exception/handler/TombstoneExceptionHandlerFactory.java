package com.tombstone.server.exception.handler;

import static com.tombstone.server.ui.admin.bean.Page.ERROR_PAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.lebcool.common.logging.Logger;
import com.tombstone.server.ui.admin.bean.ErrorPageBean;

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
             final List<Throwable> exceptions = new ArrayList<>();

             final Iterator<ExceptionQueuedEvent> iterator
                 = getUnhandledExceptionQueuedEvents().iterator();

             final boolean exceptionOccurred = iterator.hasNext();

             while(iterator.hasNext())
             {
                 final ExceptionQueuedEvent event = iterator.next();

                 final ExceptionQueuedEventContext eventContext
                     = event.getContext();

                 exceptions.add(eventContext.getException());

                 iterator.remove();
             }

             if(exceptionOccurred)
             {
                 LOGGER.info(this, "Uncaught exceptions were "
                     + "thrown.  Attempting to display the default error "
                     + "page to the user.");

                 final FacesContext facesContext
                     = FacesContext.getCurrentInstance();

                 final ExternalContext externalContext
                     = facesContext.getExternalContext();

                 final Application application = facesContext.getApplication();

                 try
                 {
                     final ErrorPageBean bean
                          = application.evaluateExpressionGet(
                              facesContext,
                              "#{errorPageBean}",
                              ErrorPageBean.class);

                     bean.setExceptions(exceptions);

                     LOGGER.info(this, "" + bean);
                     externalContext.redirect(ERROR_PAGE + ".jsf");
                 }
                 catch(final IOException e)
                 {
                     LOGGER.error(this, "An exception was encountered while "
                         + "trying to redirect to the page=\""
                         + ERROR_PAGE + "\".", e);
                 }
                 finally
                 {
                     _exceptionHandler.handle();
                 }
             }
         }

         @Override
         public String toString()
         {
             return getClass().getName();
         }

         private final ExceptionHandler _exceptionHandler;

         private static final Logger LOGGER
             = new Logger(TombstoneExceptionHandlerWrapper.class);
     }

     //:: ---------------------------------------------------------------------
     //:: Private Data Members

     private final ExceptionHandlerFactory _parent;
}
