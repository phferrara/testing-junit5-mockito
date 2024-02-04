package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {

    @Mock
    private VetRepository vetRepository;
    @InjectMocks
    private VetSDJpaService vetSDJpaService;

    private Vet vet1;

    private Vet vet2;

    @BeforeEach
    void setUp() {
        Speciality speciality1 = new Speciality("pédiatrie");
        Speciality speciality2 = new Speciality("oncologie");
        Speciality speciality3 = new Speciality("allergies");
        vet1 = new Vet(1L, "John", "Doe", Set.of(speciality1));
        vet2 = new Vet(2L, "Laura", "Hannigan", Set.of(speciality2, speciality3));
    };

    @Test
    void findAll() {
        // Given
        Set<Vet> vets = Set.of(vet1, vet2);
        when(vetRepository.findAll()).thenReturn(vets);

        // When
        Set<Vet> actualVets = vetSDJpaService.findAll();

        // Then
        List<Long> vetIds = vets.stream()
                .map(Vet::getId)
                .sorted()
                .toList();
        List<Long> actualVetIds = actualVets.stream()
                .map(Vet::getId)
                .sorted()
                .toList();
        assertIterableEquals(vetIds, actualVetIds);
        verify(vetRepository).findAll();
    }

    @Test
    void findById() {
        // Given
        long vetId = 1L;
        when(vetRepository.findById(vetId)).thenReturn(Optional.of(vet1));

        // When
        Vet actualVet = vetSDJpaService.findById(vetId);

        // Then
        assertEquals(vet1, actualVet);
        verify(vetRepository).findById(vetId);
    }

    @Test
    void save() {
        // Given
        when(vetRepository.save(vet1)).thenReturn(vet1);

        // When
        Vet actualVet = vetSDJpaService.save(vet1);

        // Then
        assertEquals(vet1, actualVet);
        verify(vetRepository).save(vet1);
    }

    @Test
    void delete() {
        // Given
        doNothing().when(vetRepository).delete(vet1);

        // When
        vetSDJpaService.delete(vet1);

        // Then
        verify(vetRepository).delete(vet1);
    }

    @Test
    void deleteById() {
        // Given
        long vetId = 1L;
        doNothing().when(vetRepository).deleteById(vetId);

        // When
        vetSDJpaService.deleteById(vetId);

        // Then
        verify(vetRepository).deleteById(vetId);
    }
}