using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Secretary.Models
{
    public class TimeTable
    {
        public string UserName { get; set; }

        public string LastName { get; set; }

        public string FullName
        {
            get { return UserName + " " + LastName; }
        }

        public string Password { get; set; }
        public Role Role { get; set; }
        public List<Subject> Subjects { get; set; }
    }
}