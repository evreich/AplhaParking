namespace AlphaParking.Models.SeedData
{
    public static class UserConstants
    {
        public static User Manager { get; }
        public static User Admin { get; }
        public static User Employee { get; }

        static UserConstants()
        {
            Manager = new User
            {
                Id = 11,
                FIO = "Adminov Manager Adminovich",
                Phone = "88005553536",
            };
            Admin = new User
            {
                Id = 12,
                FIO = "Adminov Admin Adminovich",
                Phone = "88005553535",
            };
            Employee = new User
            {
                Id = 13,
                FIO = "Adminov Employee Adminovich",
                Phone = "88005553537",
            };
        }
    }
}