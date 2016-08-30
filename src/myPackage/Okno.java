package myPackage;

//import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Okno extends JFrame implements ActionListener {

	private JButton bQuit, bOdpa, bOdpb, bOdpc, bOdpd, bInfo, bNastepne, bPoprzednie, bPierwszy, bOstatni;
	private JLabel lPytanie, lOdpa, lOdpb, lOdpc, lOdpd, lWynik;
	public static String zestaw[] = new String[5];
	public static int odp[] = new int[1];
	private static int aktID, maxID;
	private static ResultSet pyt;
	private static CzytajBaze mojaBaza;
	private int dobrze, zle;

	public Okno() {
		aktID = 1;
		maxID = 15;
		setSize(700, 250); // okienko
		setTitle("Quiz - Swing + JDBC");
		setLayout(null);

		lPytanie = new JLabel("Pytanie:");
		lPytanie.setBounds(10, 10, 700, 20);
		add(lPytanie);

		lOdpa = new JLabel("Odpowiedz A:");
		lOdpa.setBounds(10, 30, 700, 20);
		add(lOdpa);

		lOdpb = new JLabel("Odpowiedz B:");
		lOdpb.setBounds(10, 50, 700, 20);
		add(lOdpb);

		lOdpc = new JLabel("Odpowiedz C:");
		lOdpc.setBounds(10, 70, 700, 20);
		add(lOdpc);

		lOdpd = new JLabel("Odpowiedz D:");
		lOdpd.setBounds(10, 90, 700, 20);
		add(lOdpd);

		lWynik = new JLabel("Pytanie:1  Dobrze:0  èle:0");
		lWynik.setBounds(450, 160, 200, 20);
		add(lWynik);

		bOdpa = new JButton("Odp A");
		bOdpa.setBounds(10, 130, 100, 20);
		bOdpa.addActionListener(this);
		add(bOdpa);

		bOdpb = new JButton("Odp B");
		bOdpb.setBounds(120, 130, 100, 20);
		bOdpb.addActionListener(this);
		add(bOdpb);

		bOdpc = new JButton("Odp C");
		bOdpc.setBounds(230, 130, 100, 20);
		bOdpc.addActionListener(this);
		add(bOdpc);

		bOdpd = new JButton("Odp D");
		bOdpd.setBounds(340, 130, 100, 20);
		bOdpd.addActionListener(this);
		add(bOdpd);

		bInfo = new JButton("Info");
		bInfo.setBounds(450, 130, 100, 20);
		bInfo.addActionListener(this);
		add(bInfo);

		bQuit = new JButton("Wyjúcie");
		bQuit.setBounds(560, 130, 100, 20);
		bQuit.addActionListener(this);
		add(bQuit);

		bPierwszy = new JButton("Pierwsze");
		bPierwszy.setBounds(10, 160, 100, 20);
		bPierwszy.addActionListener(this);
		add(bPierwszy);

		bPoprzednie = new JButton("Poprzednie");
		bPoprzednie.setBounds(120, 160, 100, 20);
		bPoprzednie.addActionListener(this);
		add(bPoprzednie);

		bNastepne = new JButton("NastÍpne");
		bNastepne.setBounds(230, 160, 100, 20);
		bNastepne.addActionListener(this);
		add(bNastepne);

		bOstatni = new JButton("Ostatnie");
		bOstatni.setBounds(340, 160, 100, 20);
		bOstatni.addActionListener(this);
		add(bOstatni);

		mojaBaza = new CzytajBaze();
		mojaBaza.otworzBaze();
		aktualizujPoID();
	}

	public static void main(String[] args) {
		Okno okno = new Okno();
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == bQuit) {
			mojaBaza.zamknijBaze();
			dispose();
		} else if (source == bNastepne) {
			zwiekszID();
		} else if (source == bPoprzednie) {
			zmniejszID();
		} else if (source == bPierwszy) {
			aktID = 1;
			aktualizujPoID();
		} else if (source == bOstatni) {
			aktID = maxID;
			aktualizujPoID();
		} else if (source == bOdpa) {
			sprawdzOdpowiedz(1);
			zwiekszID();
		} else if (source == bOdpb) {
			sprawdzOdpowiedz(2);
			zwiekszID();
		} else if (source == bOdpc) {
			sprawdzOdpowiedz(3);
			zwiekszID();
		} else if (source == bOdpd) {
			sprawdzOdpowiedz(4);
			zwiekszID();
		} else if (source == bInfo) {
			JOptionPane.showMessageDialog(null, "Prosty przyk≥ad do obs≥ugi mySQL\nSwing + JDBC");
		}

	}

	private void sprawdzOdpowiedz(int ktora) {
		if (odp[0] == ktora) {
			JOptionPane.showMessageDialog(null, "Prawid≥owo!");
			dobrze++;
		} else {
			JOptionPane.showMessageDialog(null, "èle!");
			zle++;
		}
	}

	private void zmniejszID() {
		aktID--; // obkrÍÊ
		if (aktID < 1) {
			aktID = maxID;
		}
		aktualizujPoID();
	}

	private void zwiekszID() {
		aktID++; // jak przejedzie wszystkie pytania co cofnij do 1
		if (aktID > maxID) {
			aktID = 1;
		}
		aktualizujPoID();
	}

	private void aktualizujPoID() {
		pyt = mojaBaza.getPoID(aktID);
		mojaBaza.odczytajPoID(pyt, zestaw, odp);
		lPytanie.setText("Pyt " + aktID + ": " + zestaw[0]);
		lOdpa.setText("A: " + zestaw[1]);
		lOdpb.setText("B: " + zestaw[2]);
		lOdpc.setText("C: " + zestaw[3]);
		lOdpd.setText("D: " + zestaw[4]);
		lWynik.setText("ID: " + aktID + "    Dobrze: " + dobrze + "    èle: " + zle);
	}

}
