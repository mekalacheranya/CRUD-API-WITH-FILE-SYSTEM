package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StudentService {
    private static final String FILE_PATH = "C:\\Sample\\project\\StudentDetails\\src\\main\\resources\\students.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Student> getAllStudents() throws IOException {
        return Arrays.asList(objectMapper.readValue(new File(FILE_PATH), Student[].class));
    }
    public Student getStudentById(Long id) throws IOException {
        List<Student> students = getAllStudents();
        Optional<Student> optionalStudent = students.stream()
                                                   .filter(student -> student.getId().equals(id))
                                                   .findFirst();

        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        } else {
            throw new IOException("Student not found with id: " + id);
            // or you can return null instead of throwing an exception
            // return null;
        }
    }



    public Student createStudent(Student student) throws IOException {
        List<Student> students = getAllStudents();
        student.setId((long) (students.size() + 1));
        students.add(student);
        objectMapper.writeValue(new File(FILE_PATH), students);
        return student;
    }

    public Student updateStudent(Long id, Student studentDetails) throws IOException {
        List<Student> students = getAllStudents();
        Optional<Student> optionalStudent = students.stream()
                                                   .filter(s -> s.getId().equals(id))
                                                   .findFirst();

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setCourse(studentDetails.getCourse());
            student.setAge(studentDetails.getAge());
            objectMapper.writeValue(new File(FILE_PATH), students);
            return student;
        } else {
            throw new IOException("Student not found with id: " + id);
            // or you can return null instead of throwing an exception
            // return null;
        }
    }


    public void deleteStudent(Long id) throws IOException {
        List<Student> students = getAllStudents();
        students.removeIf(student -> student.getId().equals(id));
        objectMapper.writeValue(new File(FILE_PATH), students);
    }
}

	// @Autowired
	 //   private StudentRepository studentRepository;

	//    public List<Student> getAllStudents() {
	 //       return studentRepository.findAll();
	//    }

	//    public Optional<Student> getStudentById(Long id) {
	//        return studentRepository.findById(id);
	//    }

	//    public Student createStudent(Student student) {
	//        return studentRepository.save(student);
	 //   }
	   // public Student updateStudent(Long id, Student studentDetails) {
	   //     Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
	   //     student.setName(studentDetails.getName());
	    //    student.setAge(studentDetails.getAge());
	     //   student.setEmail(studentDetails.getEmail());
	   //     student.setCourse(studentDetails.getCourse());
	 //       return studentRepository.save(student);
	 //   }

	 //   public void deleteStudent(Long id) {
	 //       Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
	 //       studentRepository.delete(student);
	   

	


