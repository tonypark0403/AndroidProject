using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Secretary.Models
{
    public enum Role
    {
        ADMIN, USER
    }
    public class User
    {
        public int UserId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Password { get; set; }
        public Role Role { get; set; }

        public ICollection<Subject> Subjects { get; set; }
    }
}