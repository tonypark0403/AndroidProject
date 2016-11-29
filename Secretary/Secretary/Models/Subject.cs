using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Secretary.Models
{
	public class Subject
	{
        public int Id { get; set; }
	    public int UserId { get; set; }
	    public User User { get; set; }
        public string Code { get; set; }
        public string Room { get; set; }
        public int Day { get; set; }
        public int StartHour { get; set; }
        public int StartMin { get; set; }
        public int EndHour { get; set; }
        public int EndMin { get; set; }
        public int Color { get; set; }
    }
}