package ru.besuglovs.test.ttt.Timetable;

/**
 * Created by bs on 21.04.2014.
 */
public class Discipline {
    public Integer DisciplineId;
    public String Name;
    public String Attestation; // 0 - ничего; 1 - зачёт; 2 - экзамен; 3 - зачёт и экзамен
    public String AuditoriumHours;
    public String LectureHours;
    public String PracticalHours;
    public Integer StudentGroupId;

    public Discipline() {
    }
}
