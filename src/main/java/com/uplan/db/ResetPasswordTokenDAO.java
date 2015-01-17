package com.uplan.db;

import com.uplan.core.ResetPasswordToken;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by tanvimehta on 15-01-16.
 */
public class ResetPasswordTokenDAO extends AbstractDAO<ResetPasswordToken> {
    public ResetPasswordTokenDAO(SessionFactory factory) {
        super(factory);
    }

    public ResetPasswordToken persistResetToken(ResetPasswordToken resetPasswordToken) {
        return this.persist(resetPasswordToken);
    }

    public ResetPasswordToken getLatestResetPasswordToken(String email) {
        List<ResetPasswordToken> resetPasswordTokenList = namedQuery("com.uplan.core.ResetPasswordToken.latestResetToken").
                setString("email", email).list();
        if (resetPasswordTokenList != null && resetPasswordTokenList.size() > 0) {
            return resetPasswordTokenList.get(0);
        }
        return null;
    }

    public void deleteResetTokens(long id) {
        namedQuery("com.uplan.core.ResetPasswordToken.deleteResetToken").setBigInteger("reset_password_token_id", BigInteger.valueOf(id)).executeUpdate();
    }
}
