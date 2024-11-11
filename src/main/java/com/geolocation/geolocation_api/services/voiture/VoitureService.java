//package com.geolocation.geolocation_api.services.voiture;
//
//import com.geolocation.geolocation_api.entities.GpsDevice;
//import com.geolocation.geolocation_api.entities.User;
//import com.geolocation.geolocation_api.entities.Voiture;
//import com.geolocation.geolocation_api.repository.GpsDeviceRepository;
//import com.geolocation.geolocation_api.repository.UserRepository;
//import com.geolocation.geolocation_api.repository.VoitureRepository;
//import com.geolocation.geolocation_api.services.interfaces.IVoitureService;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class VoitureService implements IVoitureService {
//
//    private VoitureRepository voitureRepository ;
//    private UserRepository userRepository;
//    private GpsDeviceRepository gpsDeviceRepository;
//    @Override
//    public List<Voiture> getVoitures() {
//        return this.voitureRepository.findAll();
//    }
//
//    @Override
//    public Voiture createVoiture(Voiture voiture) {
//        return voitureRepository.save(voiture);
//    }
//
//    @Override
//    public Voiture updateVoiture(Voiture voiture, Long id) {
//
//        Optional<Voiture> voitureOptional = voitureRepository.findById(id);
//        if(voitureOptional.isPresent()){
//            Voiture voiture1 = voitureOptional.get();
//            voiture1.setNom(voiture.getNom());
//            voiture1.setUser(voiture.getUser());
//            voiture1.setMarque(voiture.getMarque());
//            voiture1.setModele(voiture.getModele());
//            voiture1.setImmatricule(voiture.getImmatricule());
//
//            return voitureRepository.save(voiture1);
//        }
//        return null;
//    }
//
//    public Voiture updateVoitureSetGpsDeviceNull( Long id) {
//
//
//        Optional<Voiture> voitureOptional = voitureRepository.findById(id);
//        if(voitureOptional.isPresent()){
//            Voiture voiture1 = voitureOptional.get();
//            voiture1.setGpsDevice(null);
//            return voitureRepository.save(voiture1);
//        }
//        return null;
//    }
//
//
//
//    public Voiture updateVoitureGpsDevice(Long gpsDeviceid, Long id) {
//
//        GpsDevice gpsDevice = gpsDeviceRepository.findById(gpsDeviceid)
//                .orElseThrow(() -> new EntityNotFoundException("GpsDevice not found"));
//        Optional<Voiture> voitureOptional = voitureRepository.findById(id);
//        if(voitureOptional.isPresent()){
//            Voiture voiture1 = voitureOptional.get();
//            voiture1.setGpsDevice(gpsDevice);
//            return voitureRepository.save(voiture1);
//        }
//        return null;
//    }
//
//    @Override
//    public Voiture createVoitureWithGPS(Voiture voiture, GpsDevice gpsDevice) {
//        Voiture voitureDejaAssocieAuGPSChoisi =  gpsDevice.getVoiture();
//        if(voitureDejaAssocieAuGPSChoisi != null){
//            voitureDejaAssocieAuGPSChoisi.setGpsDevice(null);
//            voitureRepository.save(voitureDejaAssocieAuGPSChoisi);
//        }
//        voiture.setGpsDevice(gpsDevice);
//        return voitureRepository.save(voiture);
//    }
//
//
//    @Transactional
//    @Override
//    public void deleteVoiture(Long voitureId) {
//        // Récupérer la voiture par ID
//        Voiture voiture = voitureRepository.findById(voitureId)
//                .orElseThrow(() -> new EntityNotFoundException("Voiture not found"));
//
//        // Trouver tous les GpsDevice liés à cette voiture
//        Optional<GpsDevice> gpsDevice = gpsDeviceRepository.findByVoiture(voiture);
//        if(gpsDevice.isPresent()){
//            // Déconnecter  le GpsDevice de cette voiture (mettre la relation à null)
//            gpsDevice.get().setVoiture(null);
//            gpsDeviceRepository.save(gpsDevice.get());
//        }
//
//
//
//        // Supprimer la voiture
//        voitureRepository.delete(voiture);
//    }
//
//    @Override
//    public Voiture getVoitureById(Long id) {
//        Optional<Voiture> voitureOptional = voitureRepository.findById(id);
//        return voitureOptional.orElse(null);
//    }
//
//    @Override
//    public List<Voiture> getVoitureByUserId(Long userId) {
//        Optional<User>  userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            return voitureRepository.findByUser(user);
//        }
//        return null;
//    }
//}
