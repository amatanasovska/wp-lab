package mk.ukim.finki.wp.lab.model.converters;

import mk.ukim.finki.wp.lab.model.attributeclasses.TeacherFullName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TeacherFullNameConverter implements AttributeConverter<TeacherFullName,String> {

    private static final String SEPARATOR = " ";
    @Override
    public String convertToDatabaseColumn(TeacherFullName teacherFullName) {
        if (teacherFullName== null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (teacherFullName.getName() != null && !teacherFullName.getName()
                .isEmpty()) {
            sb.append(teacherFullName.getName());
            sb.append(SEPARATOR);
        }

        if (teacherFullName.getSurname() != null
                && !teacherFullName.getSurname().isEmpty()) {
            sb.append(teacherFullName.getSurname());
        }

        return sb.toString();
    }

    @Override
    public TeacherFullName convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        String[] pieces = s.split(SEPARATOR);

        if (pieces.length == 0) {
            return null;
        }

        TeacherFullName personName = new TeacherFullName();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (s.contains(SEPARATOR)) {
            personName.setName(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                personName.setSurname(pieces[1]);
            }
        } else {
            personName.setSurname(firstPiece);
        }

        return personName;
    }
}
