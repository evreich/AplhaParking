namespace AlphaParking.Models.SeedData
{
    public static class UserConstants
    {
        public static User Manager { get; }

        static UserConstants()
        {
            Manager = new User {
                FIO = "Evreich Evreich Evreich",
                Phone = "88005553535",
            };
        }
    }
}
