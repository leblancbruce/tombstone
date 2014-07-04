package com.tombstone.server.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.LocalDateTime;

import com.lebcool.common.database.procedure.ResultSetProcessor;
import com.lebcool.common.database.procedure.StoredProcedure;
import com.lebcool.common.database.procedure.StoredProcedureArguments;
import com.lebcool.common.domain.Repository;
import com.lebcool.common.logging.Logger;
import com.tombstone.server.domain.exception.LoadException;
import com.tombstone.server.domain.exception.SaveException;

public class ImageRepository extends Repository
{
    //:: ---------------------------------------------------------------------
    //:: Public Construction

    public ImageRepository(final DataSource dataSource)
    {
        super(dataSource);
    }

    //:: ---------------------------------------------------------------------
    //:: Public Interface

    public Image loadById(final long id) throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getImageById");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(id);

        final SingleImageResultSetProcessor resultSetProcessor
            = new SingleImageResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getImage();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the image "
                + "using id=" + id + ".", e);
        }
    }

    public List<Image> loadByApplicationId(final long applicationId)
        throws LoadException
    {
        final StoredProcedure procedure
            = new StoredProcedure("getImagesByApplicationId");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(applicationId);

        final MultipleImagesResultSetProcessor resultSetProcessor
            = new MultipleImagesResultSetProcessor();

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);

            return resultSetProcessor.getImages();
        }
        catch(final SQLException e)
        {
            throw new LoadException("Unable to load the images "
                + "using applicationId=" + applicationId + ".", e);
        }
    }

    public void save(final Image image) throws SaveException
    {
        final StoredProcedure procedure
        = new StoredProcedure("saveImage");

        final StoredProcedureArguments arguments
            = new StoredProcedureArguments();
        arguments.add(image.getId());
        arguments.add(image.getVersion());
        arguments.add(image.getThumbnail());
        arguments.add(image.getImage());
        arguments.add(image.isDefaultImage());
        arguments.add(image.getApplicationUserId());
        arguments.add(image.getCemeteryId());
        arguments.add(image.getPlotId());
        arguments.add(image.getPersonId());

        final SingleImageResultSetProcessor resultSetProcessor
            = new SingleImageResultSetProcessor(image);

        final Executable executable
            = new Executable(procedure, arguments, resultSetProcessor);

        try
        {
            execute(executable);
        }
        catch(final SQLException e)
        {
            throw new SaveException("Unable to save image="
                + image + " to the database.", e);
        }
    }

    //:: ---------------------------------------------------------------------
    //:: Private Inner Classes

    private static abstract class BaseResultSetProcessor
        implements ResultSetProcessor
    {
        @Override
        public abstract void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException;

        protected void unmarshal(
            final ResultSet resultSet,
            final Image image)
                throws SQLException
        {
            LOGGER.debug(this, "Unmarshalling the row results into an "
                + "{} object.", Image.class.getName());

            final Long id = resultSet.getLong(1);
            final long version = resultSet.getLong(2);
            final LocalDateTime createdOn
                = convertStringToLocalDateTime(resultSet.getString(3));
            final LocalDateTime updatedOn
                = convertStringToLocalDateTime(resultSet.getString(4));

            updateControlledFields(
                image, id, version, createdOn, updatedOn);

            image.setThumbnail(resultSet.getBytes(5));
            image.setImage(resultSet.getBytes(6));
            image.setApplicationUserId(resultSet.getLong(7));
            image.setCemeteryId(resultSet.getLong(8));
            image.setPlotId(resultSet.getLong(9));
            image.setPersonId(resultSet.getLong(10));

            return;
        }

        private static final Logger LOGGER
            = new Logger(BaseResultSetProcessor.class);
    }

    private static final class SingleImageResultSetProcessor
        extends BaseResultSetProcessor
    {
        public SingleImageResultSetProcessor()
        {
            this(new Image());
        }

        public SingleImageResultSetProcessor(final Image image)
        {
            _image = image;
        }

        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            if(resultSet.next())
            {
                unmarshal(resultSet, _image);

                return;
            }

            _image = null;
        }

        public Image getImage()
        {
            return _image;
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private Image _image;
    }

    private static final class MultipleImagesResultSetProcessor
        extends BaseResultSetProcessor
    {
        @Override
        public void processResultSet(
            final ResultSet resultSet,
            final int resultSetId)
                throws SQLException
        {
            while(resultSet.next())
            {
                final Image image = new Image();

                unmarshal(resultSet, image);

                _images.add(image);
            }
        }

        public List<Image> getImages()
        {
            return Collections.unmodifiableList(_images);
        }

        @Override
        public String toString()
        {
            return getClass().getName();
        }

        private final List<Image> _images = new ArrayList<>();
    }
}
