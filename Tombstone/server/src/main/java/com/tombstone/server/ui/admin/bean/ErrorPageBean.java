package com.tombstone.server.ui.admin.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public final class ErrorPageBean extends UIAdminBean
{
    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public List<Throwable> getExceptions()
    {
        return Collections.unmodifiableList(_exceptions);
    }

    public void setExceptions(final List<Throwable> exceptions)
    {
        _exceptions = exceptions;
    }

    public List<PrintableException> getPrintableExceptions()
    {
        final List<PrintableException> printableExceptions
            = new ArrayList<>();

        for(final Throwable exception : _exceptions)
        {
            printableExceptions.add(new PrintableException(exception));
        }

        return Collections.unmodifiableList(printableExceptions);
    }

    //:: ---------------------------------------------------------------------
    //:: Public Nested Classes

    public static final class PrintableException
    {
        public PrintableException(final Throwable exception)
        {
            _exceptionMessage = exception.getLocalizedMessage();

            Throwable cause = exception.getCause();

            while(cause != null)
            {
                _causeMessages.add(cause.getLocalizedMessage());

                cause = cause.getCause();
            }
        }

        public String getExceptionMessage()
        {
            return _exceptionMessage;
        }

        public List<String> getCauseMessages()
        {
            return Collections.unmodifiableList(_causeMessages);
        }

        private final String _exceptionMessage;

        private final List<String> _causeMessages = new ArrayList<>();
    }

    //:: ---------------------------------------------------------------------
    //:: Private Data Members

    private List<Throwable> _exceptions = new ArrayList<>();

    //:: ---------------------------------------------------------------------
    //:: Private Constants

    private static final long serialVersionUID = 1L;
}
