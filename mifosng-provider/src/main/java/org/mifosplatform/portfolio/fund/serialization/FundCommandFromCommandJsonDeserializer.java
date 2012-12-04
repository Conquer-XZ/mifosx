package org.mifosplatform.portfolio.fund.serialization;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.serialization.FromCommandJsonDeserializer;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.portfolio.fund.command.FundCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;

@Component
public final class FundCommandFromCommandJsonDeserializer implements FromCommandJsonDeserializer<FundCommand> {

    private final FromJsonHelper fromJsonHelper;

    @Autowired
    public FundCommandFromCommandJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
        this.fromJsonHelper = fromApiJsonHelper;
    }

    @Override
    public FundCommand commandFromCommandJson(final String commandAsJson) {
        return commandFromCommandJson(null, commandAsJson);
    }

    @Override
    public FundCommand commandFromCommandJson(final Long fundId, final String commandAsJson) {
        return commandFromCommandJson(fundId, commandAsJson, false);
    }

    @Override
    public FundCommand commandFromCommandJson(final Long fundId, final String commandAsJson, final boolean makerCheckerApproval) {
        if (StringUtils.isBlank(commandAsJson)) { throw new InvalidJsonException(); }

        final Set<String> parametersPassedInRequest = new HashSet<String>();

        final JsonElement element = fromJsonHelper.parse(commandAsJson);
        
        final String name = fromJsonHelper.extractStringNamed("name", element, parametersPassedInRequest);
        final String externalId = fromJsonHelper.extractStringNamed("externalId", element, parametersPassedInRequest);

        return new FundCommand(parametersPassedInRequest, makerCheckerApproval, fundId, name, externalId);
    }
}