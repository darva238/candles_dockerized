package entity;

public class Profile {
    private Long idProfile;
    private String login;
    private String passwordHash;
    private String salt;

    public Profile() {
    }

    public Profile(Long id, String login, String passwordHash) {
        this.idProfile = id;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return idProfile;
    }

    public void setId(Long idProfile) {
        this.idProfile = idProfile;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return idProfile != null && idProfile.equals(profile.idProfile);
    }
}
