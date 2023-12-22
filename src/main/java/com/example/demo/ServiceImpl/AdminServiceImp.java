package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.AdminDTO;
import com.example.demo.Dto.HotelDto;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Entity.AdminEntity;
import com.example.demo.Entity.BookingEntity;
import com.example.demo.Entity.HotelEntity;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Repo.AdminRepo;
import com.example.demo.Repo.BookingRepo;
import com.example.demo.Repo.HotelRepo;
import com.example.demo.Repo.RoleRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.AdminService;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	HotelRepo hotelRepo;

	@Autowired
	BookingRepo bookrepo;

	@Override
	public String addAdmin(UserDTO userDto) {

//		AdminEntity ae = new AdminEntity(adminDto.getAdminName(),adminDto.getAdminEmail(),adminDto.getAdminPassword());
//		
//		adminRepo.save(ae);
//		
//		return ae.getAdminName() + ": Saved in Database";

		UserEntity userEntity = new UserEntity(

				userDto.getUserName(), userDto.getUserEmail(), userDto.getUserPassword()

		);

//		userEntity.getRoles().add(role);

//		Role role=roleRepo.findById(1).orElse(null);
//		System.out.println(role);
//	
//		userEntity.getRoles().add(role);
//		System.out.println(userEntity);

		Role role = roleRepo.findById(2).orElse(null);

		userEntity.getRoles().add(role);

		userRepo.save(userEntity);

		return "User Added :  " + userEntity.getUserId();
	}

	@Override
	public UserEntity loginAdmin(UserDTO userDto) {

		List<UserEntity> lue = userRepo.findAll();

		UserEntity ll = userRepo.findByUserEmail(userDto.getUserEmail()).orElse(null);

		for (Role r : ll.getRoles()) {
			if (r.getName().equals("ADMIN")) {
				return ll;
			}
		}

		return null;

	}

	@Override
	public HotelEntity editHotelById(HotelDto hotelDto) {

		HotelEntity he = hotelRepo.findById(hotelDto.getHotelId()).orElse(null);

		if (he != null) {
			he.setHotelName(hotelDto.getHotelName());
			he.setHotelCity(hotelDto.getHotelCity());
			he.setHotelAddress(hotelDto.getHotelAddress());
			he.setHotelId(hotelDto.getHotelId());
			he.setHotelPrice(hotelDto.getHotelPrice());
			he.setNoOfRooms(hotelDto.getNoOfRooms());

			return hotelRepo.save(he);
		}

		return null;
	}

	@Override
	public String deleteHotelById(int hotid) {

		List<BookingEntity> lbe = bookrepo.findAll();

		for (BookingEntity be : lbe) {
			if (be.getHotelE().getHotelId() == hotid) {
				bookrepo.deleteById(be.getBookingId());
			}
		}

		hotelRepo.deleteById(hotid);

		return "Hotel Deleted";
	}

	@Override
	public List<BookingEntity> allbookingdetails() {

		List<BookingEntity> lbe = bookrepo.findAll();

		return lbe;
	}

}
